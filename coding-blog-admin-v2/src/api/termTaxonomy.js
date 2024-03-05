import request from '../utils/request'

export function getTermTaxonomyPagedList(params) {
  return request({
    url: `/termTaxonomy/queryAllTermTaxonomyTree`,
    method: 'get',
    params
  })
}