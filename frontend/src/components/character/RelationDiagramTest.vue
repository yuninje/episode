<template>
  <div>
    <div class="title">
      <h1>
        <a href="https://github.com/emiliorizzo/vue-d3-network">vue-d3-network</a>
      </h1>
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
    </div>

    <d3-network
      ref="net"
      :net-nodes="nodes"
      :net-links="links"
      :selection="{nodes: selected, links: linksSelected}"
      :options="options"
      :linkCb="linkCb"
      @node-click="nodeClick"
      @link-click="linkClick"
    />
    
  </div>
</template>

<script>
import * as utils from "./utils.js";
import D3Network from "vue-d3-network";
import * as d3 from "d3";

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
      tools: {
        pointer: {
          tip: "Select",
          class: "icon-pointer"
        },
        killer: {
          tip: "Click on link or node to kill",
          class: "icon-delete_forever"
        },
        parent: {
          tip: "click on node to create parent",
          class: "icon-repo-forked"
        },
        pin: {
          tip: "click on node to pin / unpin ",
          class: "icon-pin"
        }
      },
      nodes: [
        { id: 1, name: "아이유", svgSym: nodeIcon3 },
        { id: 2, name: "my node 2" },
        { id: 3, name: "orange node", _color: "orange" },
        { id: 4, _color: "#4466ff" },
        { id: 5 },
        { id: 6 },
        { id: 7 },
        { id: 8 },
        { id: 9 }
      ],
      links: [
        {
          sid: 1,
          tid: 2,
          _color: "black"
        },
        { sid: 2, tid: 8, _color: "black" },
        {
          sid: 3,
          tid: 4,
          _svgAttrs: { "stroke-width": 1, opacity: 1 },
          name: "custom link",
          _color: "black"
        },
        { sid: 4, tid: 5, _color: "black" },
        { sid: 5, tid: 6, _color: "black" },
        { sid: 7, tid: 8, _color: "black" },
        { sid: 5, tid: 8, _color: "black" },
        { sid: 3, tid: 8, _color: "black" },
        { sid: 7, tid: 9, _color: "black" }
      ],
      nodeSize: 80,
      canvas: false
    };
  },
  computed: {
    options() {
      return {
        force: 12000, // 퍼진 정도 (첫 화면의 노드 길이 ㅠ)
        size: { w: this.window.width, h: this.window.height },
        nodeSize: this.nodeSize,
        nodeLabels: true,
        linkLabels: true,
        canvas: this.canvas,
        linkWidth: 1,
        strLinks: false
      };
    }
    // nodes() { return this.character.nodes },
    // links() { return this.relation.links },
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
      //   .attr("refX", 6)
      //   .attr("refY", 6)
      // .attr("refX", 33)
      // .attr("refY", 6)
      .attr("markerWidth", 25)
      .attr("markerHeight", 25)
      .attr("markerUnits", "userSpaceOnUse")
      .attr("orient", "auto")
      .append("path")
      // .attr("d", "M 0 0 12 6 0 12 3 6")
      .attr("d", "M0,-5L10,0L0,5")
      .style("fill", "black");
  },
  created() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.reset();
  },
  methods: {
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
      console.log(this.nodes);
    },
    nodeClick(event, node) {
      // 마우스 뗐을때    // select는 마우스 누른 순간인듯
      console.log("nodeClick()");
      // console.log("1. event", event);
      console.log("2. node", node);

      console.log(this.selected[node.id]);
      this.pinNode(node);

      if (this.selected[node.id]) {
        // this.unSelectNode(node.id)
        // is not selected
      } else {
        this.selectNode(node);
      }
      this.selectNodesLinks();

      selector %= 2;
      if (selector == 0) {
        // from
        from = node.id;
        console.log("from", from);
      } else if (selector == 1) {
        //to
        to = node.id;
        console.log("to", to);
        if (from == to) {
          console.log("연결할 캐릭터를 선택해주세요");
          return;
        }
        this.createLink(from, to);
      }
      selector++;
    },
    linkClick(event, link) {
      console.log("linkClick()");
      // console.log("1. event", event);
      console.log("2. link", link);
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
    },
    createLink(from, to) {
      for (let i = 0; i < this.links.length; i++) {
        if (this.links[i].sid == from && this.links[i].tid == to) {
          console.log("존재하는 관계입니다.", newlink);
          return;
        }
      }
      // 연결이 존재하지 않으면 새로운 링크 생성
      let newlink = { sid: from, tid: to, _color: "black" };
      this.links.push(newlink);
    },
    changeIcon(event, node) {
      node = Object.assign(node, {
        svgSym: nodeIcon3,
        svgIcon: null,
        svgObj: null
      });
      this.$set(this.nodes, node.index, node);
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
  position: absolute;
  text-align: center;
  left: 2em;
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