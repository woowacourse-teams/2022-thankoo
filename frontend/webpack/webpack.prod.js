const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const webpack = require('webpack');

module.exports = merge(common, {
  mode: 'production',
  output: {
    filename: 'bundle.[contenthash].js',
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
      'process.env.API_URL': JSON.stringify(process.env.API_URL),
      'process.env.MODE': JSON.stringify(process.env.MODE),
    }),
  ],
});
