<script setup lang="ts">
import { onMounted } from 'vue'
import { useCompaniesStore } from '../../stores/companies'

const companies = useCompaniesStore()

onMounted(() => companies.loadCompanies())
</script>

<template>
  <section>
    <p class="eyebrow">crm-core</p>
    <h1>Công ty</h1>
    <p><RouterLink to="/companies/new">Thêm công ty</RouterLink></p>

    <p v-if="companies.listStatus === 'loading'">Đang tải danh sách công ty…</p>
    <div v-else-if="companies.listStatus === 'error'">
      <p>{{ companies.listError }}</p>
      <button type="button" @click="companies.loadCompanies">Thử lại</button>
    </div>
    <p v-else-if="companies.companies.length === 0">Chưa có công ty nào.</p>
    <ul v-else>
      <li v-for="company in companies.companies" :key="company.id">
        <RouterLink :to="`/companies/${company.id}`">{{ company.name }}</RouterLink>
        <span> — {{ company.industry }}</span>
        <RouterLink :to="`/companies/${company.id}`">Xem</RouterLink>
      </li>
    </ul>
  </section>
</template>
