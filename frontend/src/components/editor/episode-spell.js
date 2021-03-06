// const axios = require("axios");
const request = require('request');
const Entities = require('html-entities').AllHtmlEntities;
const entities = new Entities();
const decode = entities.decode;

const split = require('./split-string').byWordCount;

function getJSON(response) {
    var typos = [];
  
    try {
      response = response.match(/\tdata = \[.*;/g);
      for (var i = 0; i < response.length; ++i) {
        const json = JSON.parse(response[0].substring(8, response[0].length - 1));
        for (var j = 0; j < json.length; ++j) {
          const errInfo = json[j]["errInfo"];
          for (var k = 0; k < errInfo.length; ++k) {
            var suggestions = errInfo[k]["candWord"].replace(/\|$/, '');
            if (suggestions == '') {
              suggestions = decode(errInfo[k]["orgStr"]);
            }
            const info = errInfo[k]["help"].replace(/< *[bB][rR] *\/>/g, "\n");
            const aTypo = {
              token: decode(errInfo[k]["orgStr"]),
              suggestions: decode(suggestions).split('|'),
              info: decode(info)
            };
  
            typos.push(aTypo);
          }
        }
      }
    } catch (err) {
      console.log(err)
    }
  
    return typos;
  }

  
const PUSAN_UNIV_MAX_WORDS  = 280; // passive setting, actually 300
// const PUSAN_UNIV_URL        = 'http://speller.cs.pusan.ac.kr/results';
const PUSAN_UNIV_URL        = 'http://k02a2061.p.ssafy.io/editor';

// requests spell check to the server. `check` is called at each response
// with the parsed JSON parameter.
function spellCheck(sentence, timeout, check, end, error) {
  // due to PNU server's weired logic
  const data = split(sentence.replace(/\n/g, "\n "), PUSAN_UNIV_MAX_WORDS);
  console.log(data)
  var count = data.length;

  const getResponse = function (err, response, body) {
    count--;
    if (!err && response.statusCode == 200) {
      check(getJSON(body));
    } else {
      console.error("-- 한스펠 오류: " +
        "부산대 서버 접속 오류로 일부 문장 교정 실패");
      if (error) error(err);
    }
    if (count == 0 && end != null) end();
  };

  for (var i = 0; i < data.length; ++i) {
    // axios.post(PUSAN_UNIV_URL,
    // {
    //   timeout: timeout,
    // text1: data[i]
      
    // }).then(getResponse)
    request.post({
      url: PUSAN_UNIV_URL,
      timeout: timeout,
      form: {
        text1: data[i]
      }
    }, getResponse);
  }
}

module.exports = spellCheck;