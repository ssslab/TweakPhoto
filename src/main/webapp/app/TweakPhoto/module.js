(function () {
    'use strict';

    angular
        .module('app.tweakPhoto', [])
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {

        $stateProvider
            .state('app.tweakPhoto', {
                abstract: true,
                data: {
                    title: ''
                }
            });
/*
            .state('app.terraSeller.search', {
                url: '/seller-search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Поиск продукта',
                    title: 'Поиск продукта'
                },
                views: {
                    "content@app": {
                        templateUrl: 'app/TerraSeller/search/search.html',
                        controller: 'terraSellerSearchController',
                        controllerAs: 'vm'
                    }
                }
            })

  */      
    }
})();
