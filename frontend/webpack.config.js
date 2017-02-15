const path = require('path');

module.exports = {
  entry: './src/index.js',
  devtool: 'source-map',
  cache: true,
  debug: true,
  output: {
    path: path.join(__dirname, 'dist'),
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      {
        loaders: ['babel-loader?presets[]=es2015&presets[]=react'],
        test: path.join(__dirname, 'src'),
        exclude: /(node_modules)/,
      }
    ]
  }
};