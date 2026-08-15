<script setup lang="ts">
defineProps<{ title: string; module: string }>()
</script>

<template>
  <section class="placeholder">
    <p class="eyebrow">{{ module }}</p>
    <h1>{{ title }}</h1>
    <p>Module UI khởi tạo. Kết nối REST API qua Axios/`/api` sẽ được thêm theo từng use case.</p>
  </section>
</template>

