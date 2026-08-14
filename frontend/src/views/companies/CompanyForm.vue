<script setup lang="ts">
import { nextTick, ref, watch } from 'vue'
import type { CompanyInput, CompanyType } from '../../api/companies'

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
const companyTypes: { value: CompanyType, label: string }[] = [
  { value: 'TRADITIONAL', label: 'Truyền thống' }, { value: 'IT_SOLUTION', label: 'Giải pháp CNTT' },
  { value: 'IT_PRODUCT', label: 'Sản phẩm CNTT' }, { value: 'TECH_STARTUP', label: 'Khởi nghiệp công nghệ' },
  { value: 'OTHER_ITO', label: 'ITO khác' }
]
</script>

<template>
  <form novalidate @submit.prevent="submit">
    <p v-if="fieldErrors._global" role="alert">{{ fieldErrors._global }}</p>
    <p><label for="company-name">Tên công ty <span aria-hidden="true">*</span></label><br>
      <input id="company-name" ref="nameInput" v-model="form.name" :aria-invalid="Boolean(errorFor('name'))" :aria-describedby="errorFor('name') ? 'company-name-error' : undefined">
      <span v-if="errorFor('name')" id="company-name-error" role="alert">{{ errorFor('name') }}</span></p>
    <p><label for="company-industry">Ngành <span aria-hidden="true">*</span></label><br>
      <input id="company-industry" ref="industryInput" v-model="form.industry" :aria-invalid="Boolean(errorFor('industry'))" :aria-describedby="errorFor('industry') ? 'company-industry-error' : undefined">
      <span v-if="errorFor('industry')" id="company-industry-error" role="alert">{{ errorFor('industry') }}</span></p>
    <p><label for="company-type">Loại công ty <span aria-hidden="true">*</span></label><br>
      <select id="company-type" ref="companyTypeInput" v-model="form.companyType" :aria-invalid="Boolean(errorFor('companyType'))" :aria-describedby="errorFor('companyType') ? 'company-type-error' : undefined"><option v-for="type in companyTypes" :key="type.value" :value="type.value">{{ type.label }}</option></select>
      <span v-if="errorFor('companyType')" id="company-type-error" role="alert">{{ errorFor('companyType') }}</span></p>
    <p><label for="company-country">Quốc gia</label><br><input id="company-country" ref="countryInput" v-model="form.country" :aria-invalid="Boolean(errorFor('country'))" :aria-describedby="errorFor('country') ? 'company-country-error' : undefined">
      <span v-if="errorFor('country')" id="company-country-error" role="alert">{{ errorFor('country') }}</span></p>
    <p><label for="company-website">Website</label><br><input id="company-website" ref="websiteInput" v-model="form.website" :aria-invalid="Boolean(errorFor('website'))" :aria-describedby="errorFor('website') ? 'company-website-error' : undefined">
      <span v-if="errorFor('website')" id="company-website-error" role="alert">{{ errorFor('website') }}</span></p>
    <p><label for="company-phone">Điện thoại</label><br><input id="company-phone" ref="phoneInput" v-model="form.phone" :aria-invalid="Boolean(errorFor('phone'))" :aria-describedby="errorFor('phone') ? 'company-phone-error' : undefined">
      <span v-if="errorFor('phone')" id="company-phone-error" role="alert">{{ errorFor('phone') }}</span></p>
    <p><label for="company-address">Địa chỉ</label><br><input id="company-address" ref="addressInput" v-model="form.address" :aria-invalid="Boolean(errorFor('address'))" :aria-describedby="errorFor('address') ? 'company-address-error' : undefined">
      <span v-if="errorFor('address')" id="company-address-error" role="alert">{{ errorFor('address') }}</span></p>
    <p><label for="company-description">Mô tả</label><br><textarea id="company-description" ref="descriptionInput" v-model="form.description" :aria-invalid="Boolean(errorFor('description'))" :aria-describedby="errorFor('description') ? 'company-description-error' : undefined" />
      <span v-if="errorFor('description')" id="company-description-error" role="alert">{{ errorFor('description') }}</span></p>
    <p v-if="errorFor('version')" id="company-version-error" role="alert">{{ errorFor('version') }}</p>
    <button type="submit" :disabled="pending">{{ pending ? 'Đang lưu…' : 'Lưu' }}</button>
    <button type="button" :disabled="pending" @click="emit('cancel')">Hủy</button>
  </form>
</template>
