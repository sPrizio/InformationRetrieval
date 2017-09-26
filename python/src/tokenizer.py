import nltk
import re

with open('test.sgm', 'r') as myFile:
    data = myFile.read().replace('\n', '')

datesPattern = re.compile('<DATE>([\s\S]*?)</DATE>')
dates = datesPattern.findall(data)
print dates

titlesPattern = re.compile('<TITLE>([a-zA-z\d\s]*)</TITLE>')
titles = titlesPattern.findall(data)
print titles

bodiesPattern = re.compile('<BODY>([\s\S]*?)</BODY>')
bodies = bodiesPattern.findall(data)
print bodies

# sentence = "At eight o'clock on Thursday morning Arthur didn't feel very good."
# tokens = nltk.word_tokenize(sentence)
# print tokens
# tagged = nltk.pos_tag(tokens)
# print tagged[0:6]
