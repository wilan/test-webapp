var tradesController = function($scope, $http){
  this.http_ = $http;
  this.trades = null;
  this.getTrades();
}

tradesController.prototype.getTrades = function() {
	this.http_.get('/trade').then(function(response) {
		this.trades = response.data;
	}.bind(this), function(error) {
		console.log(error);
	});
}

tradesController.prototype.create = function(value) {
	this.http_.post('/trade', {value: value}).then(function(response) {
		alert('Created trade with id: ' + response.data.id);
		this.getTrades();
	}.bind(this), function(error) {
		alert('Error creating trade');
	});
}

tradesController.prototype.read = function(id) {
	var config = {};
	config.params = {};
	config.params.id = id;
	this.http_.get('/trade', config).then(function(response) {
		alert('Value is ' + response.data.value);
	}.bind(this), function(id, error) {
		alert('Error invalid id: ' + id);
	}.bind(this, id));
}

tradesController.prototype.update = function(id, newValue) {
	this.http_.put('/trade', {id: id, value: newValue}).then(function(response) {
		alert('Updated trade.');
		this.getTrades();
	}.bind(this), function(id, error) {
		alert('Error invalid id: ' + id);
	}.bind(this, id));
}

tradesController.prototype.deleteTrade = function(id) {
	var config = {};
	config.params = {};
	config.params.id = id;
	this.http_.delete('/trade', config).then(function(id, response) {
		alert('Trade with value ' + id + ' was deleted.');
		this.getTrades();
	}.bind(this, id), function(id, error) {
		alert('Error invalid id: ' + id);
	}.bind(this, id));
}