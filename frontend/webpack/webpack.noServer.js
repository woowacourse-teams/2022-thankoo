const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const webpack = require('webpack');
const dotenv = require('dotenv');
const { join } = require('path');

dotenv.config({ path: join(__dirname, '../env/.env.noServer') });
module.exports = merge(common, {
  mode: 'development',
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
      'process.env.API_URL': JSON.stringify(process.env.API_URL),
      'process.env.MODE': JSON.stringify(process.env.MODE),
    }),
  ],
});
