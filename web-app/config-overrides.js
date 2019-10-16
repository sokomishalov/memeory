const {override: customizeCRA, fixBabelImports, disableEsLint} = require('customize-cra');

module.exports = function override(config) {
    disableEsLint()

    config.module.rules.push({
        loader: 'webpack-ant-icon-loader',
        enforce: 'pre',
        include: [
            require.resolve('@ant-design/icons/lib/dist')
        ]
    });

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