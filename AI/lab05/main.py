import functions
import utils

if __name__ == '__main__':
	fileName = 'data/sport.txt'
	dic = utils.readFile(fileName)
	print(dic)
	if (fileName == 'data/sport.txt'):
		print('Regression:')
		mae, rmse = functions.compute_error(dic)
		print('MAE: ', mae, '\nRMSE: ', rmse)
	elif (fileName == 'data/flowers.csv'):
		print('\nClassification:')
		accuracy, precision, recall = functions.evalClassification(dic[0], dic[1],
		                                                           ['Daisy', 'Rose',
		                                                            'Tulip'])
		print('Accuracy: ', accuracy, '\nPrecision: ', precision, '\nRecall: ', recall)
	
	sports = utils.readFromCSV('data/sport.txt')
	sports.pop(0)
	errorL1_weights, errorL1_waists, errorL1_pulses, errorL2_weights, errorL2_waists, \
		errorL2_pulses = functions.regressionV2(sports)
	print('L1:')
	print('Weights: ', errorL1_weights)
	print('Waists: ', errorL1_waists)
	print('Pulses: ', errorL1_pulses)
	print('Error', errorL1_weights + errorL1_waists + errorL1_pulses)
	
	print('L2:')
	print('Weights: ', errorL2_weights)
	print('Waists: ', errorL2_waists)
	print('Pulses: ', errorL2_pulses)
	print('Error', errorL2_weights + errorL2_waists + errorL2_pulses)
	
	probBinary = 'data/probabilities-binary.txt'
	trueBinary = 'data/true-binary.txt'
	r1, p1 = utils.readFile2(probBinary)
	real, predicted = utils.readTxt(trueBinary)
	loss = functions.lossClassification(real, predicted)
	loss1 = functions.lossV2(real, predicted, r1, p1)
	print('Loss for the binary classification', probBinary, trueBinary, 'is', loss,
	      loss1)
	
	probMultiClass = 'data/probabilities-multi-class.txt'
	trueMultiClass = 'data/true-multi-class.txt'
	mulitClassLoss = functions.multiClassLoss(utils.readMultiClass(probMultiClass),
	                                          utils.readMultiClass(trueMultiClass))
	print('Multi class loss for ', probMultiClass, trueMultiClass, 'is',
	      mulitClassLoss)
	
	probMultiTarget = 'data/probabilities-multi-target.txt'
	trueMultiTarget = 'data/true-multi-target.txt'
	multiTgLoss = functions.multiTargetLoss(utils.readMultiTarget(probMultiTarget),
	                                        utils.readMultiTarget(trueMultiTarget))
	print('Multi target loss for ', probMultiTarget, trueMultiTarget, 'is',
	      multiTgLoss)
