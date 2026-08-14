import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import ModuleView from './views/ModuleView.vue'
import CompanyDetailView from './views/companies/CompanyDetailView.vue'
import CompanyFormView from './views/companies/CompanyFormView.vue'
import CompaniesListView from './views/companies/CompaniesListView.vue'

const moduleRoutes = [
  ['/', 'Dashboard & Tổng quan', 'dashboard'],
  ['/pipeline', 'Cơ hội & Pipeline', 'crm-core'],
  ['/timeline', 'Hoạt động & Timeline', 'crm-core'],
  ['/insights', 'AI Insight', 'observation'],
  ['/proposals', 'Gợi ý chờ duyệt', 'proposal'],
  ['/admin', 'Admin & Cấu hình', 'admin-safety']
].map(([path, title, module]) => ({ path, component: ModuleView, props: { title, module } }))

const routes: RouteRecordRaw[] = [
  ...moduleRoutes,
  { path: '/companies', component: CompaniesListView },
  { path: '/companies/new', component: CompanyFormView },
  { path: '/companies/:id(\\d+)/edit', component: CompanyFormView, props: route => ({ id: Number(route.params.id) }) },
  { path: '/companies/:id(\\d+)', component: CompanyDetailView, props: route => ({ id: Number(route.params.id) }) }
]

export default createRouter({ history: createWebHistory(), routes })
