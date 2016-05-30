"use strict";

angular.module('app').factory('Language', function($http, APP_CONFIG, $log){

	function getLanguage(key, callback) {

		$http.get(APP_CONFIG.apiRootUrl + '/langs/' + key + '.json').success(function(data){

			callback(data);

		}).error(function(){

			$log.log('Error');
			callback([]);

		});

	}

	function getLanguages(callback) {

		$http.get(APP_CONFIG.apiRootUrl + '/languages.json').success(function(data){

			callback(data);

		}).error(function(){

			$log.log('Error');
			callback([]);

		});

	}

    function getLangByKey(langKey, callback) {
        getLanguages(function (data) {
            if(data){
                angular.forEach(data, function(langData){
                    if (langData.key == langKey) {
                        callback(langData);
                    }
                });
            } else {
                callback([]);
            }
        });
    }

	return {
		getLang: function(type, callback) {
			getLanguage(type, callback);
		},
        getLangByKey: function(langKey, callback) {
            getLangByKey(langKey, callback);
        },
		getLanguages:function(callback){
			getLanguages(callback);
		}
	}

});
