module.exports = {
  purge: {
    content: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
    enabled: process.env.NODE_ENV === "production",
  },
  darkMode: 'media',
  theme: {
    container: {
      center: true,
      padding: "0.5rem",
    },
  },
  variants: {
    extend: {},
  },
  plugins: [require('@tailwindcss/forms')],
}
