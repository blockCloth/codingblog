// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: {
    browser: true,
  },
  extends: [
    // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
    // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
    'plugin:vue/essential', 
    // 'plugin:vue/recommended', 
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    'standard'
  ],
  // required to lint *.vue files
  plugins: [
    'vue'
  ],
  // add your custom rules here
  rules: {
    // allow async-await
    'generator-star-spacing': 'off',
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-trailing-spaces':'off',
    'indent':'off',
    'spaced-comment':'off',
    'no-new':'off',
    'key-spacing':'off',
    'comma-dangle':'off',
    'eol-last':'off',
    'quotes':'off',
    'semi':'off',
    'keyword-spacing':'off',
    'space-before-blocks':'off',
    'comma-spacing':'off',
    'space-before-function-paren':'off',
    'space-infix-ops':'off',
    'no-multiple-empty-lines':'off',
    'block-spacing':'off',
    'padded-blocks':'off',
    'arrow-spacing':'off',
    'no-sequences':'off',
    'no-unused-expressions':'off',
    'no-multi-spaces':'off',
    'no-unused-vars':'off',
    'camelcase ':'off',
    'eqeqeq':'off',
  }
}
