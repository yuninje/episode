<template>
  <div class="mywork">
    <div class="work">
      <div class="work-category">전체작품</div>
      <section class="work-section">
        <div class="card-grid">
          <a v-for="item in all" :key="item.title" class="card" href="#">
            <div
              class="card__background"
              style="background-image: url(https://images.unsplash.com/photo-1557177324-56c542165309?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80)"
            ></div>
            <div class="card__content">
              <h3 class="card__heading">{{ item.title }}</h3>
            </div>
          </a>

          <a class="card" href="#">
          <div class="card__background" style="border: solid 1px rgb(192, 0, 0)"></div>
          <div class="card__content">+</div>
        </a>
        </div>
      </section>
    </div>

    <div class="work">
      <div class="work-category">연재 중 작품</div>
      <section class="work-section">
        <div class="card-grid">
          <a v-for="item in publish" :key="item.title" class="card" href="#">
            <div
              class="card__background"
              style="background-image: url(https://images.unsplash.com/photo-1557187666-4fd70cf76254?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60)"
            ></div>
            <div class="card__content">
              <h3 class="card__heading">{{ item.title }}</h3>
            </div>
          </a>

          <a class="card" href="#">
          <div class="card__background" style="border: solid 1px rgb(192, 0, 0)"></div>
          <div class="card__content">+</div>
        </a>
        </div>
      </section>
    </div>

    <div class="work">
      <div class="work-category">작성 중 작품</div>
      <section class="work-section">
        <div class="card-grid">
          <a v-for="item in write" :key="item.title" class="card" href="#">
            <div
              class="card__background"
              style="background-image: url(https://images.unsplash.com/photo-1556680262-9990363a3e6d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60)"
            ></div>
            <div class="card__content">
              <h3 class="card__heading">{{ item.title }}</h3>
            </div>
          </a>

          <a class="card" href="#">
          <div class="card__background" style="border: solid 1px rgb(192, 0, 0)"></div>
          <div class="card__content">+</div>
        </a>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      all: [
        { title: "전체작품 소설 1" },
        { title: "전체작품 소설 2" },
        { title: "전체작품 소설 3" }
      ],
      publish: [
        { title: "연재중인 소설 1" },
        { title: "연재중인 소설 2" },
        { title: "연재중인 소설 3" }
      ],
      write: [
        { title: "작성중인 소설 1" },
        { title: "작성중인 소설 2" },
        { title: "작성중인 소설 3" }
      ]
    };
  },
  created() {
    savedId: this.$session.get("savedId");
  },
  computed: {},
  methods: {}
};
</script>

<style lang="scss" scoped>
.mywork {
  // --background-dark: #2d3548;
  --text-light: rgba(255, 255, 255, 0.6);
  --text-lighter: rgba(255, 255, 255, 0.9);
  --spacing-s: 8px;
  --spacing-m: 16px;
  --spacing-l: 24px;
  --spacing-xl: 32px;
  --spacing-xxl: 64px;
  --width-container: 1200px;

  width: 80%;
  margin-top: 50px;
  margin-left: auto;
  margin-right: auto;
}

.work-category {
  font-weight: 700;
}
.work-section {
  align-items: flex-start;
  display: flex;
  min-height: 100%;
  justify-content: center;
  padding-top: var(--spacing-s);
  padding-bottom: var(--spacing-xl)
  // padding: var(--spacing-l) var(--spacing-l);
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  grid-column-gap: var(--spacing-l);
  grid-row-gap: var(--spacing-l);
  max-width: var(--width-container);
  width: 100%;
}

@media (min-width: 540px) {
  .card-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 960px) {
  .card-grid {
    grid-template-columns: repeat(6, 1fr);
  }
}

.card {
  list-style: none;
  position: relative;
}

.card:before {
  content: "";
  display: block;
  padding-bottom: 150%;
  width: 100%;
}

.card__background {
  background-size: cover;
  background-position: center;
  border-radius: var(--spacing-m);
  bottom: 0;
  filter: brightness(0.75) saturate(1.2) contrast(0.85);
  left: 0;
  position: absolute;
  right: 0;
  top: 0;
  transform-origin: center;
  trsnsform: scale(1) translateZ(0);
  transition: filter 200ms linear, transform 200ms linear;
}

.card:hover .card__background {
  transform: scale(1.03) translateZ(0);
}

.card-grid:hover > .card:not(:hover) .card__background {
  filter: brightness(0.5) saturate(0) contrast(1.2) blur(0px);
}

.card__content {
  left: 0;
  padding: var(--spacing-m);
  position: absolute;
  top: 0;
}

.card__category {
  color: var(--text-light);
  font-size: 0.9rem;
  margin-bottom: var(--spacing-s);
  text-transform: uppercase;
}

.card__heading {
  color: var(--text-lighter);
  font-size: 1.2rem;
  font-weight: bold;
  text-shadow: 2px 2px 20px rgba(0, 0, 0, 0.2);
  line-height: 1.4;
  word-spacing: 100vw;
}
</style>
