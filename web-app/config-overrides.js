const {override: customizeCRA, fixBabelImports} = require('customize-cra');
const ModuleScopePlugin = require('react-dev-utils/ModuleScopePlugin');

module.exports = function override(config) {
    config.module.rules.push({
        loader: 'webpack-ant-icon-loader',
        enforce: 'pre',
        include: [
            require.resolve('@ant-design/icons/lib/dist')
        ]
    });

    config.resolve.plugins = config.resolve.plugins.filter(plugin => !(plugin instanceof ModuleScopePlugin));

    return customizeCRA(
        fixBabelImports("lodash", {
            libraryDirectory: "",
            camel2DashComponentName: false
        }),
        fixBabelImports("antd", {
            libraryName: "antd",
            libraryDirectory: "es"
        }),
        fixBabelImports("antd-mobile", {
            libraryName: "antd-mobile",
            libraryDirectory: "lib"
        })
    )(config);
};