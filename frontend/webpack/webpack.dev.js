const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');
const { join } = require('path');
const mode = process.env.NODE_ENV || 'development';

dotenv.config({ path: join(__dirname, '../env/.env.development') });

module.exports = merge(common, {
  mode: 'production',
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
      'process.env.API_URL': JSON.stringify('http://54.180.89.42'),
      'process.env.MODE': JSON.stringify('development'),
    }),
  ],
});
