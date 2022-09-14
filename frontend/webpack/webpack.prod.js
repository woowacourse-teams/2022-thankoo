const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');
const mode = process.env.NODE_ENV || 'development';

module.exports = merge(common, {
  mode: 'production',
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
      'process.env.API_URL': JSON.stringify('https://api-thankoo.co.kr'),
      'process.env.MODE': JSON.stringify('production'),
    }),
  ],
});
