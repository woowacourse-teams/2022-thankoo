const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const webpack = require('webpack');
const dotenv = require('dotenv');
const { join } = require('path');

dotenv.config({ path: join(__dirname, '../env/.env.development') });

module.exports = merge(common, {
  mode: 'production',
  devtool: 'eval-cheap-source-map',
  optimization: { minimize: false },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
      'process.env.API_URL': JSON.stringify(process.env.API_URL),
      'process.env.MODE': JSON.stringify('development'),
    }),
  ],
});
