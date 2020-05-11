<template>
  <div class="editor">
    에디터 페이지 입니다.
    <quillEditor />
  </div>
</template>

<script>
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import { quillEditor } from 'vue-quill-editor'
export default {
  components: {
    quillEditor
  },
  data () {
    return {

    }
  }
}
</script>

<style lang="scss" scoped>

</style>
