<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { isAxiosError } from 'axios'
import type { Company } from '../../api/companies'
import { useCompaniesStore } from '../../stores/companies'
import { companyTypeLabel, companyTypeTagClass } from '../../utils/companyType'
import CompanyDeleteDialog from './CompanyDeleteDialog.vue'

const companies = useCompaniesStore()
const companyToDelete = ref<Company | null>(null)
const deleting = ref(false)
const deleteError = ref('')

onMounted(() => companies.loadCompanies())

const rowNumber = (index: number) => String(index + 1).padStart(2, '0')

async function confirmDelete() {
  if (!companyToDelete.value) return
  deleting.value = true
  deleteError.value = ''
  try {
    await companies.deleteCompany(companyToDelete.value.id)
    companyToDelete.value = null
  } catch (error) {
    deleteError.value = isAxiosError(error) && error.response?.data?.message || 'Không thể xóa công ty.'
  } finally {
    deleting.value = false
  }
}
</script>

<template>
  <section>
    <div class="page-header">
      <div>
        <h1 class="page-title">Công ty</h1>
        <p class="page-subtitle">{{ companies.companies.length }} công ty</p>
      </div>
      <RouterLink to="/companies/new" class="btn btn-primary"><span aria-hidden="true">＋</span> Tạo công ty</RouterLink>
    </div>

    <p v-if="deleteError" class="alert alert-error" role="alert">{{ deleteError }}</p>

    <div v-if="companies.listStatus === 'loading'" class="card table-card">
      <div v-for="n in 5" :key="n" class="skeleton-row">
        <div class="skeleton-block" style="width: 6%" />
        <div class="skeleton-block" style="width: 20%" />
        <div class="skeleton-block" style="width: 20%" />
        <div class="skeleton-block" style="width: 14%" />
        <div class="skeleton-block" style="width: 14%" />
        <div class="skeleton-block" style="width: 20%" />
      </div>
    </div>

    <div v-else-if="companies.listStatus === 'error'" class="card state-card state-card--retry">
      <div class="state-icon">↻</div>
      <p class="state-title">Không thể tải dữ liệu</p>
      <p class="state-body">{{ companies.listError }}</p>
      <button type="button" class="btn btn-secondary" @click="companies.loadCompanies">Thử lại</button>
    </div>

    <div v-else-if="companies.companies.length === 0" class="card state-card state-card--empty">
      <div class="state-icon">＋</div>
      <p class="state-title">Chưa có công ty</p>
      <p class="state-body">Tạo công ty đầu tiên để bắt đầu quản lý.</p>
      <RouterLink to="/companies/new" class="btn btn-primary">Tạo công ty</RouterLink>
    </div>

    <div v-else class="card table-card">
      <table>
        <thead>
          <tr>
            <th>#</th><th>Tên công ty</th><th>Ngành</th><th>Loại</th><th>Quốc gia</th><th>Website</th><th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(company, index) in companies.companies" :key="company.id">
            <td class="index">{{ rowNumber(index) }}</td>
            <td><RouterLink :to="`/companies/${company.id}`">{{ company.name }}</RouterLink></td>
            <td>{{ company.industry }}</td>
            <td><span class="tag" :class="companyTypeTagClass(company.companyType)">{{ companyTypeLabel(company.companyType) }}</span></td>
            <td>{{ company.country || '—' }}</td>
            <td class="mono">{{ company.website || '—' }}</td>
            <td class="actions">
              <RouterLink :to="`/companies/${company.id}`" class="action-link"><span aria-hidden="true">◉</span> Xem</RouterLink>
              <RouterLink :to="`/companies/${company.id}/edit`" class="action-link"><span aria-hidden="true">✎</span> Sửa</RouterLink>
              <button type="button" class="action-link action-link--danger" @click="companyToDelete = company"><span aria-hidden="true">▣</span> Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <CompanyDeleteDialog
      v-if="companyToDelete"
      :company-name="companyToDelete.name"
      :pending="deleting"
      @confirm="confirmDelete"
      @cancel="companyToDelete = null"
    />
  </section>
</template>
