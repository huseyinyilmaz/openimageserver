function validateName(name){
	pattern=/^[a-zA-Z_0-9]+$/;
	return pattern.test(name);
};
function isNumber(numberString){
	pattern=/^[0-9]+$/;
	return pattern.test(numberString);
};