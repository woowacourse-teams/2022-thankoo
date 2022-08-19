module.exports = {
  presets: [
    ['@babel/preset-react', { runtime: 'automatic', importSource: '@emotion/react' }],
    ['@emotion/babel-preset-css-prop'],
    '@babel/preset-env',
    '@babel/preset-typescript',
  ],
  plugins: ['@emotion/babel-plugin'],
};
