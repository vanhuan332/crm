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
    <section role="dialog" aria-modal="true" aria-labelledby="delete-company-title" @keydown="trapFocus">
      <h2 id="delete-company-title">Xóa công ty</h2>
      <p>Bạn có chắc muốn xóa công ty “{{ companyName }}”?</p>
      <button ref="cancelButton" type="button" :disabled="pending" @click="emit('cancel')">Hủy</button>
      <button ref="confirmButton" type="button" :disabled="pending" @click="emit('confirm')">{{ pending ? 'Đang xóa…' : 'Xóa công ty' }}</button>
    </section>
  </div>
</template>
