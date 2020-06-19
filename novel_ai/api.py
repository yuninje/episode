import numpy as np
import tensorflow as tf
import json

from flask import Flask, request, jsonify
from flask_restful import Api, Resource, reqparse


from keras.callbacks import LambdaCallback
from keras.models import Sequential, load_model
from keras.layers import Dense, LSTM
from keras.optimizers import RMSprop
from keras.utils.data_utils import get_file

import random
import sys
import io
import re

def sample(preds, temperature=1.0):
    # helper function to sample an index from a probability array
    preds = np.asarray(preds).astype('float64')
    preds = np.log(preds) / temperature
    exp_preds = np.exp(preds)
    preds = exp_preds / np.sum(exp_preds)
    probas = np.random.multinomial(1, preds, 1)
    return np.argmax(probas)
    
def init():
    global deep_learning_model, deep_learning_graph, target_names, chars, char_indices, indices_char
    deep_learning_model = load_model('bts_model_16.h5') # 저장된 모델 로딩

    path = 'bts2.txt'
    with io.open(path, encoding='utf-8') as f:
        text = f.read().lower()

    text = re.sub(r'<.*>', '', text)
    text = re.sub(r'\n', ' ', text)
    text = re.sub(r' +', ' ', text)
    text = re.sub(r'=', '', text)

    print('corpus length:', len(text))

    chars = sorted(list(set(text)))
    print('total chars:', len(chars))
    char_indices = dict((c, i) for i, c in enumerate(chars))
    indices_char = dict((i, c) for i, c in enumerate(chars))

app = Flask('My First AI App')
api = Api(app)

@app.route('/test', methods=['POST'])
def test():
    print("소설 생성중.........")
    userInput = request.get_json()
    print("시드 : " + userInput["seed"][-40:])
    maxlen = 40
    generated = ''
    sentence = userInput["seed"][-40:]
    generated += sentence
    for i in range(400):
        x_pred = np.zeros((1, maxlen, len(chars)))
        for t, char in enumerate(sentence):
            x_pred[0, t, char_indices[char]] = 1.

        preds = deep_learning_model.predict(x_pred, verbose=0)[0]
        next_index = sample(preds, 0.5)
        next_char = indices_char[next_index]

        generated += next_char
        sentence = sentence[1:] + next_char

    #     sys.stdout.write(next_char)
    #     sys.stdout.flush()
    print("소설 생성 완료!!!!!!!!!!")
    print("AI 소설 결과 : "+generated)

    result = {
        'seed': userInput["seed"][-40:],
        'novel': generated
    }
    return json.dumps(result, ensure_ascii=False)

if __name__ == '__main__':
    init() # 초기화
    app.run(host='0.0.0.0', port=8000, debug=True) # 8000포트로 실행