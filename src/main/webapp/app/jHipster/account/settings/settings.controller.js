(function() {
    'use strict';

    angular
        .module('app.jHipster')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['$rootScope', 'Principal', 'Auth', 'Language', '$translate'];

    function SettingsController ($rootScope, Principal, Auth, Language, $translate) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
            if ($rootScope.settingsAccount&&($rootScope.settingsAccount.langKey != vm.settingsAccount.langKey)) {
                vm.settingsAccount.langKey = $rootScope.settingsAccount.langKey;
            }
        });

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                    $rootScope.settingsAccount = vm.settingsAccount;

                    if($rootScope.currentLanguage.key != vm.settingsAccount.langKey) {
                        Language.getLangByKey(vm.settingsAccount.langKey, function(langData){
                            $rootScope.currentLanguage = langData;

                            Language.getLang(langData.key, function(data){

                                $rootScope.lang = data;
                            });
                        });
                    }
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }
    }
})();
