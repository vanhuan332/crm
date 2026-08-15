<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { isAxiosError } from 'axios'
import { useRouter } from 'vue-router'
import { useCompaniesStore } from '../../stores/companies'
import { companyTypeLabel, companyTypeTagClass } from '../../utils/companyType'
import CompanyDeleteDialog from './CompanyDeleteDialog.vue'

const props = defineProps<{ id: number }>()
const companies = useCompaniesStore()
const router = useRouter()
const showDeleteDialog = ref(false)
const deleting = ref(false)
const deleteError = ref('')
const showSavedBanner = computed(() => companies.savedCompanyId === props.id)

const loadCompany = () => companies.loadCompany(props.id)

onMounted(loadCompany)
watch(() => props.id, loadCompany)
onBeforeUnmount(() => {
  if (companies.savedCompanyId === props.id) companies.savedCompanyId = null
})

function formatDateTime(value: string) {
  return new Intl.DateTimeFormat('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' }).format(new Date(value))
}

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
    <p class="breadcrumb"><RouterLink to="/companies">Công ty</RouterLink> › <strong>{{ companies.company?.name }}</strong></p>

    <div v-if="showSavedBanner" class="alert alert-success">
      <span class="check" aria-hidden="true">✓</span>
      <span><strong>Lưu thành công</strong>Thông tin {{ companies.company?.name }} đã được lưu.</span>
    </div>
    <p v-if="deleteError" class="alert alert-error" role="alert">{{ deleteError }}</p>

    <div v-if="companies.detailStatus === 'loading'" class="card">
      <div v-for="n in 3" :key="n" class="skeleton-row">
        <div class="skeleton-block" style="width: 100%" />
      </div>
    </div>

    <div v-else-if="companies.detailStatus === 'notFound'" class="card state-card state-card--not-found">
      <div class="state-icon">!</div>
      <h1 class="state-title">Không tìm thấy công ty</h1>
      <p class="state-body">{{ companies.detailError }}</p>
      <RouterLink to="/companies" class="btn btn-secondary">Về danh sách</RouterLink>
    </div>

    <div v-else-if="companies.detailStatus === 'error'" class="card state-card state-card--error">
      <div class="state-icon">!</div>
      <h1 class="state-title">Không thể tải thông tin công ty</h1>
      <p class="state-body">{{ companies.detailError }}</p>
      <button type="button" class="btn btn-secondary" @click="loadCompany">Thử lại</button>
    </div>

    <div v-else-if="companies.company">
      <div class="card">
        <div class="detail-header">
          <div class="detail-icon" aria-hidden="true">▦</div>
          <div class="detail-heading">
            <h1>{{ companies.company.name }}</h1>
            <span class="tag" :class="companyTypeTagClass(companies.company.companyType)">{{ companyTypeLabel(companies.company.companyType) }}</span>
            <span class="industry">{{ companies.company.industry }}</span>
          </div>
          <div class="detail-actions">
            <RouterLink :to="`/companies/${companies.company.id}/edit`" class="btn btn-secondary"><span aria-hidden="true">✎</span> Chỉnh sửa</RouterLink>
            <button type="button" class="btn btn-danger-outline" @click="showDeleteDialog = true"><span aria-hidden="true">▣</span> Xóa công ty</button>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-section">
          <div class="section-title"><span class="bar" /><h2>Thông tin chung</h2></div>
          <div class="info-grid">
            <div class="info-item"><span class="label">Ngành</span><span class="value">{{ companies.company.industry }}</span></div>
            <div class="info-item"><span class="label">Quốc gia</span><span class="value" :class="{ empty: !companies.company.country }">{{ companies.company.country || '—' }}</span></div>
            <div class="info-item"><span class="label">Website</span><span class="value" :class="{ empty: !companies.company.website }">{{ companies.company.website || '—' }}</span></div>
            <div class="info-item"><span class="label">Điện thoại</span><span class="value" :class="{ empty: !companies.company.phone }">{{ companies.company.phone || '—' }}</span></div>
          </div>
          <div class="info-grid full" style="margin-top: 28px">
            <div class="info-item"><span class="label">Địa chỉ</span><span class="value" :class="{ empty: !companies.company.address }">{{ companies.company.address || '—' }}</span></div>
            <div class="info-item"><span class="label">Mô tả</span><span class="value" :class="{ empty: !companies.company.description }">{{ companies.company.description || '—' }}</span></div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-section">
          <div class="section-title muted"><span class="bar" /><h2>Metadata</h2></div>
          <div class="metadata-grid">
            <div><span class="label">Mã hệ thống</span><span class="value">c{{ String(companies.company.id).padStart(3, '0') }}</span></div>
            <div><span class="label">Ngày tạo</span><span class="value">{{ formatDateTime(companies.company.createdAt) }}</span></div>
            <div><span class="label">Ngày cập nhật</span><span class="value">{{ formatDateTime(companies.company.updatedAt) }}</span></div>
          </div>
        </div>
      </div>

      <div class="upcoming-grid">
        <div class="upcoming-card"><div class="icon" aria-hidden="true">+</div><p class="title">Người liên hệ</p><p class="note">Sẽ bổ sung sau</p></div>
        <div class="upcoming-card"><div class="icon" aria-hidden="true">+</div><p class="title">Cơ hội</p><p class="note">Sẽ bổ sung sau</p></div>
        <div class="upcoming-card"><div class="icon" aria-hidden="true">+</div><p class="title">Timeline</p><p class="note">Sẽ bổ sung sau</p></div>
      </div>

      <CompanyDeleteDialog v-if="showDeleteDialog" :company-name="companies.company.name" :pending="deleting" @confirm="deleteCompany" @cancel="showDeleteDialog = false" />
    </div>
  </section>
</template>
