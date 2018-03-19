const WEBPACK = require('webpack');
const PATH = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const BUILD_DIR = PATH.resolve(__dirname, 'target/assets');
const APP_DIR = PATH.resolve(__dirname, 'src/client/app');


const CONFIG = {
  entry: APP_DIR + '/index.jsx',

  output: {
    path: BUILD_DIR,
    filename: 'bundle.js'
  },
  resolve: {
    extensions: ['.js', '.json', '.jsx', '.es6']
  },
  module: {
    rules: [
      {
        test: [/\.jsx?/],
        include: APP_DIR,
        loader: 'babel-loader'
      }
    ]
  },
  plugins: [
    new CopyWebpackPlugin([{'from': 'src/client/index.html', 'to': 'index.html'}], {})
  ]

};

module.exports = CONFIG;
