<template>
  <div>
    <!-- <div class="title">
      <ul class="menu">
        <li>
          <label>Node size</label>
          <input type="range" min="1" max="100" v-model="nodeSize">
          {{ options.nodeSize }}
        </li>
        <li>
          <label>Render as</label>
          <input type="radio" :value="false" v-model="canvas">
          <label>SVG</label>
          <input type="radio" :value="true" v-model="canvas">
          <label>Canvas</label>
        </li>
      </ul>
    </div> -->
    <h1> 인물 관계도 </h1>
    <div class="wrap-rlt-dg">
      <d3-network
        ref="net"
        :net-nodes="nodes"
        :net-links="mlinks"
        :selection="{nodes: selected, links: linksSelected}"
        :options="options"
        :linkCb="linkCb"
        @node-click="nodeClick"
        @link-click="linkClick"
      />
    </div>

    
    
  </div>
</template>

<script>
import * as d3 from "d3";
import D3Network from "vue-d3-network";
import { mapActions, mapMutations, mapGetters } from "vuex";

var from, to;
var selector = 0;

const nodeIcon3 =
  '<svg version="1.1" xmlns="http://www.w3.org/2000/svg" width="20" height="32" viewBox="0 0 20 32"><image xlink:href="https://img4.yna.co.kr/photo/cms/2019/05/02/02/PCM20190502000402370_P2.jpg" x="0" y="0" height="45px" width="45px"/></svg>';
export default {
  components: {
    D3Network
  },
  data() {
    return {
      window: {
        width: 0,
        height: 0
      },
      nodeSize: 80,
      nodes:[],
      mlinks:[],
    };
  },
  computed: {
    ...mapGetters("storeNovChar", {
      charInfo: "getCharacterInfo",
      relationInfo: "getRelationInfo"
    }),
    options() {
      return {
        force: 10000, // 퍼진 정도 (첫 화면의 노드 길이 ㅠ)
        size: { w: this.window.width, h: this.window.height },
        nodeSize: this.nodeSize,
        nodeLabels: true,
        linkLabels: true,
        linkWidth: 1,
        strLinks: false
      };
    }
  },
  mounted() {
    var svg = d3.select("svg");
    // var node = svg.selectAll(".node").data(this.nodes);
    // var path = svg.selectAll("path").data(this.links);

    // 화살표 표시 define
    svg
      .append("svg:defs")
      .append("svg:marker")
      .attr("id", "m-end") // linke의 끝부분 id: m-end ㅠㅠ
      .attr("viewBox", "0 -5 10 10")
      .attr("refX", 23) // 화살표가 link의 끝에 겹치지 않도록 한다.
      .attr("refY", -1.5)
      .attr("markerWidth", 25)
      .attr("markerHeight", 25)
      .attr("markerUnits", "userSpaceOnUse")
      .attr("orient", "auto")
      .append("path")
      .attr("d", "M0,-5L10,0L0,5")
      .style("fill", "black");
  },
  created() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.reset();
  },
  watch: {
    charInfo: function() {
      this.makeNodes();
    },
    relationInfo: function() {
      this.makeLinks();
    }
  },
  methods: {
    // 캐릭터 정보가 변경되면 노드 데이터를 다시 만든다
    makeNodes() {
      // 초기화
      this.nodes=[];
      // id, name, svgSym
      let cid, cname, csvgSym
      for(let i=0; i<this.charInfo.length; ++i) {
        let ni = this.charInfo[i]

        cid = ni.characterPk
        cname = ni.characterName
        csvgSym = ni.characterImage
        let newNode = { id: cid, name: cname, svgSym: nodeIcon3 }
        this.nodes.push(newNode)
      }
    },
    // 릴레이션 정보가 변경되면 링크 데이터를 다시 만든다
    makeLinks() {
      // 초기화
      this.mlinks=[];
      // sid, tid, name
      let from, to, rltName
      for(let i=0; i<this.relationInfo.length; ++i) {
        let ri = this.relationInfo[i]

        from = ri.who.characterPk
        to = ri.whom.characterPk
        rltName = ri.relationrName
        let newlink = { sid: from, tid: to, name: rltName, _color: "black" };
        this.mlinks.push(newlink);
      }
    },
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
    },
    reset() {
      this.selected = {};
      this.linksSelected = {};
    },
    selection() {
      return {
        nodes: this.selected,
        links: this.linksSelected
      };
    },
    selectNode(node) {
      this.selected[node.id] = node;
    },
    selectNodesLinks() {
      for (let link of this.links) {
        // node is selected
        if (this.selected[link.sid] || this.selected[link.tid]) {
          this.selectLink(link);
          // node is not selected
        } else {
          this.unSelectLink(link.id);
        }
      }
    },
    selectLink(link) {
      this.$set(this.linksSelected, link.id, link);
    },
    unSelectLink(linkId) {
      if (this.linksSelected[linkId]) {
        delete this.linksSelected[linkId];
      }
    },
    pinNode(node) {
      if (!node.pinned) {
        node.pinned = true;
        node.fx = node.x;
        node.fy = node.y;
      } else {
        node.pinned = false;
        node.fx = null;
        node.fy = null;
      }
      this.nodes[node.index] = node;
      console.log(this.nodes[node.index]);
    },
    nodeClick(event, node) {
      // 마우스 뗐을때    // select는 마우스 누른 순간인듯
      // console.log(this.selected[node.id]);
      this.pinNode(node); 
    },
    linkClick(event, link) {
      console.log("linkClick()");
      // console.log("1. event", event);
      // console.log("2. link", link);
    },
    linkCb(link) {
      // link
      link.source = link.sid;
      link.target = link.tid;
      link._svgAttrs = {
        "marker-start": "url(#m-start)",
        "marker-end": "url(#m-end)",
        fill: "none"
      };
      return link;
    }
  }
};
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=PT+Sans");

body {
  font-family: "PT Sans", sans-serif;
  background-color: #eee;
}
.title {
  position: relative;
  text-align: center;
  left: 2em;
}
.wrap-rlt-dg {
  position: relative;
}
h1,
a {
  color: #1aad8d;
  text-decoration: none;
}

ul.menu {
  list-style: none;
  position: absolute;
  z-index: 100;
  min-width: 20em;
  text-align: left;
}
ul.menu li {
  margin-top: 1em;
  position: relative;
}
</style>