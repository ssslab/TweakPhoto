"use strict";

angular.module('app').controller("LanguagesCtrl",  function LanguagesCtrl($scope, $rootScope, $log, Language, Principal, Auth){

    $rootScope.lang = {};

    function setLangEnv(langData) {
        $rootScope.currentLanguage = langData;

        Language.getLang(langData.key,function(data){

            $rootScope.lang = data;
        });
    }

    Language.getLanguages(function(data){

        if (Principal.isAuthenticated()&&$rootScope.settingsAccount) {
            Language.getLangByKey($rootScope.settingsAccount.langKey, function(data){
                setLangEnv(data);
            })
        } else
        {
            setLangEnv(data[0]);
        }

        $rootScope.languages = data;
    });

    $scope.selectLanguage = function(language){
        $rootScope.currentLanguage = language;

        if (Principal.isAuthenticated()&&$rootScope.settingsAccount) {
            $rootScope.settingsAccount.langKey = language.key;
            Auth.updateAccount($rootScope.settingsAccount);
        }

        Language.getLang(language.key,function(data){

            $rootScope.lang = data;

        });
    }

    $rootScope.getWord = function(key){
        if(angular.isDefined($rootScope.lang[key])){
            return $rootScope.lang[key];
        }
        else {
            return key;
        }
    }

});
