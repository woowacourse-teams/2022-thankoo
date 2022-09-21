const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');
const mode = process.env.NODE_ENV || 'development';

module.exports = merge(common, {
  mode: 'production',
  output: {
    filename: 'bundle.[Contenthash].js',
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
      'process.env.API_URL': JSON.stringify(process.env.API_URL),
      'process.env.MODE': JSON.stringify(process.env.MODE),
    }),
  ],
});
