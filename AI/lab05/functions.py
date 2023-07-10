from math import sqrt
from utils import getSubLists, getRealValues, getPredictedValues
import numpy as np
from numpy import log


def compute_error(dict):
	mae = 0  # mean absolute error
	rmse = 0  # root mean squared error
	for index in range(3):
		mae += sum(abs(r - c) for r, c in zip(dict[index], dict[index + 3])) / len(
			dict[index])
		rmse += sqrt(
			sum((r - c) ** 2 for r, c in zip(dict[index], dict[index + 3])) / len(
				dict[index]))
	return mae, rmse


def regressionV2(outputs):
	subList, subListP = getSubLists(outputs)
	realWeights, realWaist, realPulse = getRealValues(subList)
	predictedWeights, predictedWaist, predictedPulse = getPredictedValues(subListP)
	
	errorL1_weights = sum(
		abs(r - p) for r, p in zip(realWeights, predictedWeights)) / len(realWeights)
	errorL1_waists = sum(abs(r - p) for r, p in zip(realWaist, predictedWaist)) / len(
		realWaist)
	errorL1_pulses = sum(abs(r - p) for r, p in zip(realPulse, predictedPulse)) / len(
		realPulse)
	
	errorL2_weights = sqrt(
		sum((r - p) ** 2 for r, p in zip(realWeights, predictedWeights)) / len(
			realWeights))
	errorL2_waists = sqrt(
		sum((r - p) ** 2 for r, p in zip(realWaist, predictedWaist)) / len(realWaist))
	errorL2_pulses = sqrt(
		sum((r - p) ** 2 for r, p in zip(realPulse, predictedPulse)) / len(realPulse))
	
	return errorL1_weights, errorL1_waists, errorL1_pulses, errorL2_weights, errorL2_waists, errorL2_pulses


def evalClassification(realLabels, computedLabels, labelNames):
	accuracy = sum([1 if realLabels[i] == computedLabels[i] else 0 for i in
	                range(len(realLabels))]) / len(realLabels)
	
	precision = []
	recall = []
	
	for label in labelNames:
		TP = 0
		FP = 0
		FN = 0
		for real, computed in zip(realLabels, computedLabels):
			if real == label:
				if computed == label:
					TP += 1
				else:
					FN += 1
			else:
				if computed == label:
					FP += 1
		precision.append(TP / (TP + FP))
		recall.append(TP / (TP + FN))
	
	return accuracy, precision, recall


def lossClassification(real, predicted):
	entropy = 0
	epsilon = np.finfo(np.float32).eps
	for rl, pr in zip(real, predicted):
		entropy += -(int(rl) * log(int(pr) + epsilon) + (1 - int(rl)) * log(
			1 - int(pr) + epsilon))
	return entropy / len(real)


def lossV2(real, predicted, realProb, predictedProb):
	entropy = 0
	for rProb, pProb, in zip(realProb, predictedProb):
		for rl, pd in zip(real, predicted):
			entropy += -(rl * log(pProb) + (1 - rl) * log(1 - pProb))
	return entropy / len(real)


def multiClassLoss(probArr, predArr):
	epsilon = np.finfo(np.float32).eps
	return -np.mean(np.sum(probArr * np.log(predArr + epsilon), axis=1))


def multiTargetLoss(probArr, predArr):
	epsilon = np.finfo(np.float32).eps
	return -np.mean(np.sum(
		probArr * np.log(predArr + epsilon) + (1 - probArr) * np.log(
			1 - predArr + epsilon), axis=1))
