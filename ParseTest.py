from pyfcm import FCMNotification
from parse_rest.connection import register
from parse_rest.datatypes import Object, File
from parse_rest.user import User
import os
os.environ["PARSE_API_ROOT"] = "http://52.39.188.22:80/parse"
import time


APPLICATION_ID = 'faaebb9d3559326b129581046ad907a32e7439cf'
REST_API_KEY = 'test'
MASTER_KEY = 'f0f296dfd98ef92c22ed42c3c8dedc71efadb5f0'

register(APPLICATION_ID, REST_API_KEY, master_key=MASTER_KEY)

# u = User.login("username", "password")

# from parse_rest.user import User

# u = User.signup("dhelmet", "12345")

class GameScore(Object):
	myClassName = "GameScore"
	myClass = Object.factory(myClassName)

	print myClass
	# <class 'parse_rest.datatypes.GameScore'>
	print myClass.__name__
	# GameScore

gameScore = GameScore(score=1337)
gameScore.save()

