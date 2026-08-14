import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', {
  state: () => ({ aiEnabled: true }),
  actions: { setAiEnabled(enabled: boolean) { this.aiEnabled = enabled } }
})
