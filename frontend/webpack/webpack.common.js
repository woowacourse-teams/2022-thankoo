const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');
const { join } = require('path');
const mode = process.env.NODE_ENV || 'development';

module.exports = {
  mode,
  entry: './src/index.tsx',
  output: {
    publicPath: '/',
    path: path.join(__dirname, '../dist'),
    filename: 'bundle.js',
    clean: true,
  },
  devServer: {
    historyApiFallback: true,
    port: 3000,
    hot: true,
  },
  devtool: 'source-map',
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
    modules: [path.resolve(__dirname, 'src'), 'node_modules'],
  },
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.tsx?$/,
        use: ['babel-loader', 'ts-loader'],
      },
      {
        test: /\.(png|jpe?g|gif|svg|ico|webp)$/i,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[name].[ext]',
            },
          },
        ],
      },
    ],
  },
  plugins: [
    new webpack.ProvidePlugin({
      React: 'react',
    }),
    new HtmlWebpackPlugin({
      template: './public/index.html',
    }),
    new webpack.HotModuleReplacementPlugin(),
  ],
};
