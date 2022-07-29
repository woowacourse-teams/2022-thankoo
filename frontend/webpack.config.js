const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');
const mode = process.env.NODE_ENV || 'development';

module.exports = env => {
  const { MODE } = env;

  if (MODE === 'local') {
    dotenv.config({ path: './env/.env.local' });
  } else if (MODE === 'development') {
    dotenv.config({ path: './env/.env.development' });
  } else {
    dotenv.config();
  }

  return {
    mode,
    entry: './src/index.tsx',
    output: {
      publicPath: '/',
      path: path.join(__dirname, '/dist'),
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
          test: /\.(png|jpe?g|gif|svg|ico)$/i,
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
      new webpack.EnvironmentPlugin({
        API_URL: process.env.API_URL,
        MODE: MODE ?? 'production',
      }),
    ],
  };
};
