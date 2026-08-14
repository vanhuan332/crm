<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { isAxiosError } from 'axios'
import { useRouter } from 'vue-router'
import type { CompanyInput } from '../../api/companies'
import { useCompaniesStore } from '../../stores/companies'
import CompanyForm from './CompanyForm.vue'

const props = defineProps<{ id?: number }>()
const router = useRouter()
const companies = useCompaniesStore()
const pending = ref(false)
const fieldErrors = ref<Record<string, string>>({})
const isEdit = computed(() => props.id !== undefined)

async function load() {
  if (isEdit.value) await companies.loadCompany(props.id!)
}
onMounted(load)
watch(() => props.id, load)

function getFieldErrors(error: unknown) {
  if (isAxiosError(error) && error.response?.data && typeof error.response.data === 'object') {
    const data = error.response.data as { fieldErrors?: Record<string, string>, message?: string }
    return data.fieldErrors || { _global: data.message || 'Không thể lưu công ty.' }
  }
  return { _global: 'Không thể lưu công ty.' }
}

async function save(input: CompanyInput) {
  pending.value = true
  fieldErrors.value = {}
  try {
    const company = isEdit.value ? await companies.updateCompany(props.id!, input) : await companies.createCompany(input)
    await router.push(`/companies/${company.id}`)
  } catch (error) {
    fieldErrors.value = getFieldErrors(error)
  } finally {
    pending.value = false
  }
}

function cancel() {
  router.push(isEdit.value ? `/companies/${props.id}` : '/companies')
}
</script>

<template>
  <section>
    <p class="eyebrow">crm-core</p>
    <h1>{{ isEdit ? 'Chỉnh sửa công ty' : 'Thêm công ty' }}</h1>
    <p v-if="isEdit && companies.detailStatus === 'loading'">Đang tải công ty…</p>
    <div v-else-if="isEdit && (companies.detailStatus === 'notFound' || companies.detailStatus === 'error')">
      <p>{{ companies.detailError }}</p><button type="button" @click="load">Thử lại</button>
    </div>
    <CompanyForm v-else-if="!isEdit || companies.company" :initial-values="isEdit ? companies.company! : undefined" :field-errors="fieldErrors" :pending="pending" @submit="save" @cancel="cancel" />
  </section>
</template>
