const HtmlPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const path = require('path');

module.exports = {
    entry: [
        __dirname + '/js/App.js'
    ],

    output: {
        filename: 'App.js',
        path: path.resolve(__dirname, '../public')
    },

    devtool: 'source-map',

    mode: 'development',

    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.css$/,
                use: [MiniCssExtractPlugin.loader, "css-loader"]
            }

        ]
    },

    plugins: [
        new HtmlPlugin({
            template: "index.html"
        }),

        new MiniCssExtractPlugin({
            filename: "[name].css",
            chunkFilename: "[id].css"
        })
    ]
}