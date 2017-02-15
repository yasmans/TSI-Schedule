var path = require('path');
var webpack = require('webpack');
var express = require('express');
var config = require('./webpack.dev.config');

var app = express();
var compiler = webpack(config);

app.use(require('webpack-dev-middleware')(compiler, {
  publicPath: config.output.publicPath,
  hot: true,
  historyApiFallback: true,
  proxy: {
    "*": "http://localhost:8080"
  }
}));

app.get('*', function(req, res) {
  res.sendFile(path.join(__dirname, 'assets/index-template.html'));
});

app.listen(3000, function(err) {
  if (err) {
    return console.error(err);
  }

  console.log('Listening at http://localhost:3000/');
});