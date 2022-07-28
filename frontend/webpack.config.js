const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const mode = process.env.NODE_ENV || 'development';
const dotenv = require('dotenv');

module.exports = env => {
  const { MODE } = env;

  dotenv.config();

  if (MODE === 'local') {
    dotenv.config({ path: './env/.env.local' });
  }
  if (MODE === 'development') {
    dotenv.config({ path: './env/.env.development' });
  }

  return {
    mode,
    devServer: {
      historyApiFallback: true,
      port: 3000,
      hot: true,
    },
    devtool: 'source-map',
    entry: './src/index.tsx',

    resolve: {
      extensions: ['.js', '.jsx', '.ts', '.tsx'],
      modules: [path.resolve(__dirname, 'src'), 'node_modules'],
    },

    module: {
      rules: [
        {
          test: /\.tsx?$/,
          use: ['babel-loader', 'ts-loader'],
        },
        {
          test: /\.(png|jpe?g|gif|svg)$/i,
          use: [
            {
              loader: 'file-loader',
            },
          ],
        },
      ],
    },

    output: {
      publicPath: '/',
      path: path.join(__dirname, '/dist'),
      filename: 'bundle.js',
    },

    plugins: [
      new webpack.ProvidePlugin({
        React: 'react',
      }),
      new HtmlWebpackPlugin({
        template: './public/index.html',
      }),
      new webpack.HotModuleReplacementPlugin(),
      new webpack.DefinePlugin({
        'process.env.API_URL': JSON.stringify(process.env.API_URL),
      }),
    ],
  };
};
