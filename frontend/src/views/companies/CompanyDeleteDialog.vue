<script setup lang="ts">
import { nextTick, onMounted, ref } from 'vue'

defineProps<{ companyName: string, pending?: boolean }>()
const emit = defineEmits<{ confirm: [], cancel: [] }>()
const cancelButton = ref<HTMLButtonElement | null>(null)
const confirmButton = ref<HTMLButtonElement | null>(null)

onMounted(() => nextTick(() => cancelButton.value?.focus()))

function trapFocus(event: KeyboardEvent) {
  if (event.key !== 'Tab') return
  if (event.shiftKey && document.activeElement === cancelButton.value) {
    event.preventDefault(); confirmButton.value?.focus()
  } else if (!event.shiftKey && document.activeElement === confirmButton.value) {
    event.preventDefault(); cancelButton.value?.focus()
  }
}
</script>

<template>
  <div class="dialog-backdrop">
    <section class="dialog" role="dialog" aria-modal="true" aria-labelledby="delete-company-title" @keydown="trapFocus">
      <div class="dialog-icon" aria-hidden="true">!</div>
      <h2 id="delete-company-title">Xóa {{ companyName }}?</h2>
      <p>Công ty sẽ bị gỡ khỏi danh sách.</p>
      <p class="dialog-hint">Dữ liệu liên quan được giữ nguyên để bảo toàn lịch sử.</p>
      <div class="dialog-actions">
        <button ref="cancelButton" type="button" class="btn btn-secondary" :disabled="pending" @click="emit('cancel')">Hủy</button>
        <button ref="confirmButton" type="button" class="btn btn-danger" :disabled="pending" @click="emit('confirm')">{{ pending ? 'Đang xóa…' : 'Xóa công ty' }}</button>
      </div>
    </section>
  </div>
</template>
