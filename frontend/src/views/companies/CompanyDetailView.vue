<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { isAxiosError } from 'axios'
import { useRouter } from 'vue-router'
import { useCompaniesStore } from '../../stores/companies'
import CompanyDeleteDialog from './CompanyDeleteDialog.vue'

const props = defineProps<{ id: number }>()
const companies = useCompaniesStore()
const router = useRouter()
const showDeleteDialog = ref(false)
const deleting = ref(false)
const deleteError = ref('')

const loadCompany = () => companies.loadCompany(props.id)

onMounted(loadCompany)
watch(() => props.id, loadCompany)

async function deleteCompany() {
  deleting.value = true
  deleteError.value = ''
  showDeleteDialog.value = false
  try {
    await companies.deleteCompany(props.id)
    await router.push('/companies')
  } catch (error) {
    deleteError.value = isAxiosError(error) && error.response?.data?.message || 'Không thể xóa công ty.'
  } finally {
    deleting.value = false
  }
}
</script>

<template>
  <section>
    <p class="eyebrow">crm-core</p>
    <p><RouterLink to="/companies">← Danh sách công ty</RouterLink></p>

    <p v-if="companies.detailStatus === 'loading'">Đang tải công ty…</p>
    <div v-else-if="companies.detailStatus === 'notFound'">
      <h1>Không tìm thấy công ty</h1>
      <p>{{ companies.detailError }}</p>
    </div>
    <div v-else-if="companies.detailStatus === 'error'">
      <h1>Không thể tải thông tin công ty</h1>
      <p>{{ companies.detailError }}</p>
      <button type="button" @click="loadCompany">Thử lại</button>
    </div>
    <div v-else-if="companies.company">
      <h1>{{ companies.company.name }}</h1>
      <p><RouterLink :to="`/companies/${companies.company.id}/edit`">Chỉnh sửa</RouterLink>
        <button type="button" @click="showDeleteDialog = true">Xóa công ty</button></p>
      <p v-if="deleteError" role="alert">{{ deleteError }}</p>
      <dl>
        <dt>Ngành</dt><dd>{{ companies.company.industry }}</dd>
        <dt>Loại công ty</dt><dd>{{ companies.company.companyType }}</dd>
        <dt>Quốc gia</dt><dd>{{ companies.company.country || '—' }}</dd>
        <dt>Website</dt><dd>{{ companies.company.website || '—' }}</dd>
        <dt>Điện thoại</dt><dd>{{ companies.company.phone || '—' }}</dd>
        <dt>Địa chỉ</dt><dd>{{ companies.company.address || '—' }}</dd>
        <dt>Mô tả</dt><dd>{{ companies.company.description || '—' }}</dd>
      </dl>
      <CompanyDeleteDialog v-if="showDeleteDialog" :company-name="companies.company.name" :pending="deleting" @confirm="deleteCompany" @cancel="showDeleteDialog = false" />
    </div>
  </section>
</template>
