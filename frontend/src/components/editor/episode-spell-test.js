const episodeSpell = require('./episode-spell');
// const episodeSpell = require('./daum-spell-check');


const sentence = "갈 곳 없는 인외 존재들을 이유 불문하고 거둬 주는 카페에서 40년간 외국인 노동자로 일해 온 뱀파이어 브릴린은 퇴사하기 위해 전력을 다해 불공정 근로계약서를 찾아 헤매고 있다. 그러던 어느 날, 브릴린은 카페에 방문한 한 여성의 권유로 웬 수상한 종교 단체에 갔다가 염라대왕의 업경을 깨고 도망친 귀신에게 목숨의 위협을 받게 되는데.";
const end = function () {
    console.log("// check ends");
};
const error = function (err) {
    console.error("HTTP status code: " + err);
};


episodeSpell(sentence, 6000, console.log, end, error);