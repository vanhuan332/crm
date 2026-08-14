<script setup lang="ts">
const navigation = [
  ['/', 'Tổng quan'], ['/companies', 'Công ty'], ['/pipeline', 'Cơ hội'],
  ['/timeline', 'Timeline'], ['/insights', 'AI Insight'], ['/proposals', 'Gợi ý'], ['/admin', 'Admin']
]
</script>

<template>
  <main>
    <header><div><strong>AI-NATIVE CRM</strong><span>HBLAB</span></div><small>Vue.js 3 SPA</small></header>
    <nav><RouterLink v-for="[to, label] in navigation" :key="to" :to="to">{{ label }}</RouterLink></nav>
    <RouterView />
  </main>
</template>

