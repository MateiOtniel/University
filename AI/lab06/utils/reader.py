import pandas as pd

pd.options.mode.chained_assignment = None  # default='warn'


def loadAsDF(filePath):
	df = pd.read_csv(filePath)
	df_binary = df[['Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score']]
	df_binary.head()
	df_binary.fillna(method='ffill', inplace=True)
	
	df_binary.dropna(inplace=True)
	
	gdp = [df_binary.iat[i, 0] for i in range(len(df_binary))]
	freedom = [df_binary.iat[i, 1] for i in range(len(df_binary))]
	happiness = [df_binary.iat[i, 2] for i in range(len(df_binary))]
	
	return gdp, freedom, happiness
