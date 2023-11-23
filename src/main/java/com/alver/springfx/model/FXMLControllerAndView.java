package com.alver.springfx.model;

import java.util.function.Supplier;

public interface FXMLControllerAndView<Controller, View> {

	Controller controller();
	View view();

	static <C, V> Simple<C, V> simple(C controller, V view){
		return new Simple<>(controller, view);
	}
	static <C, V> Lazy<C, V> lazy(Supplier<FXMLControllerAndView<C, V>> supplier){
		return new Lazy<>(supplier);
	}

	class Simple<Controller, View> implements FXMLControllerAndView<Controller, View> {
		private final Controller controller;
		private final View view;

		public Simple(Controller controller, View view){
			this.controller = controller;
			this.view = view;
		}

		@Override
		public Controller controller() {
			return controller;
		}
		@Override
		public View view() {
			return view;
		}
	}


	class Lazy<Controller, View> implements FXMLControllerAndView<Controller, View> {

		private final Supplier<FXMLControllerAndView<Controller, View>> supplier;
		private FXMLControllerAndView<Controller, View> inner = null;

		public Lazy(Supplier<FXMLControllerAndView<Controller, View>> supplier) {
			this.supplier = supplier;
		}

		@Override
		public Controller controller() {
			return get().controller();
		}
		@Override
		public View view() {
			return get().view();
		}
		protected synchronized FXMLControllerAndView<Controller, View> get() {
			if (inner == null) {
				inner = supplier.get();
			}
			return inner;
		}
	}
}