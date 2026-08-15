<script setup lang="ts">
import { nextTick, ref, watch } from 'vue'
import type { CompanyInput } from '../../api/companies'
import { companyTypeOptions } from '../../utils/companyType'

const props = withDefaults(defineProps<{
  initialValues?: Partial<CompanyInput>
  fieldErrors?: Record<string, string>
  pending?: boolean
}>(), { initialValues: () => ({}), fieldErrors: () => ({}), pending: false })

const emit = defineEmits<{ submit: [input: CompanyInput], cancel: [] }>()

const form = ref<CompanyInput>({
  name: '', industry: '', companyType: 'TRADITIONAL', country: null, website: null,
  phone: null, address: null, description: null, version: 0
})
const clientErrors = ref<Record<string, string>>({})
const nameInput = ref<HTMLInputElement | null>(null)
const industryInput = ref<HTMLInputElement | null>(null)
const companyTypeInput = ref<HTMLSelectElement | null>(null)
const countryInput = ref<HTMLInputElement | null>(null)
const websiteInput = ref<HTMLInputElement | null>(null)
const phoneInput = ref<HTMLInputElement | null>(null)
const addressInput = ref<HTMLInputElement | null>(null)
const descriptionInput = ref<HTMLTextAreaElement | null>(null)

const applyInitialValues = () => {
  form.value = { ...form.value, ...props.initialValues }
}

applyInitialValues()
watch(() => props.initialValues, applyInitialValues, { deep: true })
watch(() => props.fieldErrors, async errors => {
  if (Object.keys(errors).length) {
    await nextTick()
    firstInvalidInput(errors)?.focus()
  }
}, { deep: true })

function inputForField(field: string): HTMLElement | null {
  const inputs: Record<string, { value: HTMLElement | null }> = {
    name: nameInput, industry: industryInput, companyType: companyTypeInput, country: countryInput,
    website: websiteInput, phone: phoneInput, address: addressInput, description: descriptionInput
  }
  return inputs[field]?.value || null
}

function firstInvalidInput(errors: Record<string, string>) {
  const field = ['name', 'industry', 'companyType', 'country', 'website', 'phone', 'address', 'description']
    .find(candidate => errors[candidate])
  return field ? inputForField(field) : (errors.version ? nameInput.value : null)
}

function optional(value: string | null) {
  return value?.trim() || null
}

function submit() {
  clientErrors.value = {}
  if (!form.value.name.trim()) clientErrors.value.name = 'Tên công ty là bắt buộc'
  if (!form.value.industry.trim()) clientErrors.value.industry = 'Ngành là bắt buộc'
  if (Object.keys(clientErrors.value).length) {
    nextTick(() => firstInvalidInput(clientErrors.value)?.focus())
    return
  }

  emit('submit', {
    ...form.value,
    name: form.value.name.trim(),
    industry: form.value.industry.trim(),
    country: optional(form.value.country), website: optional(form.value.website),
    phone: optional(form.value.phone), address: optional(form.value.address), description: optional(form.value.description)
  })
}

const errorFor = (field: string) => clientErrors.value[field] || props.fieldErrors[field]
</script>

<template>
  <form novalidate @submit.prevent="submit">
    <p v-if="fieldErrors._global" class="alert alert-error" role="alert">{{ fieldErrors._global }}</p>

    <div class="card">
      <div class="card-section">
        <div class="section-title"><span class="bar" /><h2>Thông tin bắt buộc</h2></div>

        <div class="field">
          <label for="company-name">Tên công ty <span class="required" aria-hidden="true">*</span></label>
          <input id="company-name" ref="nameInput" v-model="form.name" placeholder="Nhập tên công ty" :aria-invalid="Boolean(errorFor('name'))" :aria-describedby="errorFor('name') ? 'company-name-error' : undefined">
          <span v-if="errorFor('name')" id="company-name-error" class="field-error" role="alert">{{ errorFor('name') }}</span>
        </div>

        <div class="field">
          <label for="company-industry">Ngành <span class="required" aria-hidden="true">*</span></label>
          <input id="company-industry" ref="industryInput" v-model="form.industry" placeholder="Nhập ngành hoạt động" :aria-invalid="Boolean(errorFor('industry'))" :aria-describedby="errorFor('industry') ? 'company-industry-error' : undefined">
          <span v-if="errorFor('industry')" id="company-industry-error" class="field-error" role="alert">{{ errorFor('industry') }}</span>
        </div>

        <div class="field">
          <label for="company-type">Loại công ty <span class="required" aria-hidden="true">*</span></label>
          <select id="company-type" ref="companyTypeInput" v-model="form.companyType" :aria-invalid="Boolean(errorFor('companyType'))" :aria-describedby="errorFor('companyType') ? 'company-type-error' : undefined">
            <option v-for="type in companyTypeOptions" :key="type.value" :value="type.value">{{ type.label }}</option>
          </select>
          <span v-if="errorFor('companyType')" id="company-type-error" class="field-error" role="alert">{{ errorFor('companyType') }}</span>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="card-section">
        <div class="section-title muted"><span class="bar" /><h2>Thông tin bổ sung</h2><span class="hint">(tùy chọn)</span></div>

        <div class="field-row">
          <div class="field">
            <label for="company-country">Quốc gia</label>
            <input id="company-country" ref="countryInput" v-model="form.country" placeholder="Ví dụ: Việt Nam" :aria-invalid="Boolean(errorFor('country'))" :aria-describedby="errorFor('country') ? 'company-country-error' : undefined">
            <span v-if="errorFor('country')" id="company-country-error" class="field-error" role="alert">{{ errorFor('country') }}</span>
          </div>
          <div class="field">
            <label for="company-phone">Điện thoại</label>
            <input id="company-phone" ref="phoneInput" v-model="form.phone" placeholder="Ví dụ: 024 3123 4567" :aria-invalid="Boolean(errorFor('phone'))" :aria-describedby="errorFor('phone') ? 'company-phone-error' : undefined">
            <span v-if="errorFor('phone')" id="company-phone-error" class="field-error" role="alert">{{ errorFor('phone') }}</span>
          </div>
        </div>

        <div class="field">
          <label for="company-website">Website</label>
          <input id="company-website" ref="websiteInput" v-model="form.website" placeholder="https://example.com" :aria-invalid="Boolean(errorFor('website'))" :aria-describedby="errorFor('website') ? 'company-website-error' : undefined">
          <span v-if="errorFor('website')" id="company-website-error" class="field-error" role="alert">{{ errorFor('website') }}</span>
        </div>

        <div class="field">
          <label for="company-address">Địa chỉ</label>
          <input id="company-address" ref="addressInput" v-model="form.address" placeholder="Địa chỉ trụ sở" :aria-invalid="Boolean(errorFor('address'))" :aria-describedby="errorFor('address') ? 'company-address-error' : undefined">
          <span v-if="errorFor('address')" id="company-address-error" class="field-error" role="alert">{{ errorFor('address') }}</span>
        </div>

        <div class="field">
          <label for="company-description">Mô tả</label>
          <textarea id="company-description" ref="descriptionInput" v-model="form.description" placeholder="Ghi chú thêm về công ty" :aria-invalid="Boolean(errorFor('description'))" :aria-describedby="errorFor('description') ? 'company-description-error' : undefined" />
          <span v-if="errorFor('description')" id="company-description-error" class="field-error" role="alert">{{ errorFor('description') }}</span>
        </div>

        <p v-if="errorFor('version')" id="company-version-error" class="field-error" role="alert">{{ errorFor('version') }}</p>

        <div class="form-footer">
          <span class="hint"><span class="required">*</span> Bắt buộc</span>
          <div class="form-actions">
            <button type="button" class="btn btn-secondary" :disabled="pending" @click="emit('cancel')">Hủy</button>
            <button type="submit" class="btn btn-primary" :disabled="pending">{{ pending ? 'Đang lưu…' : 'Lưu' }}</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>
