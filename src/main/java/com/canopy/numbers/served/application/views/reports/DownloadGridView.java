package com.canopy.numbers.served.application.views.reports;

import java.io.ByteArrayInputStream;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;

public abstract class DownloadGridView<T> extends VerticalLayout implements RouterLayout {

	private static final long serialVersionUID = -5321292302408689804L;

	protected StreamRegistration registration;

	/**
	 * Generate an Excel file in byte[] format for the given item.
	 */
	protected abstract byte[] generateExcel(T item);

	/**
	 * Initiates the download service for the selected item.
	 */
	protected void callDownloadService(T selected) {
		byte[] excelBytes = generateExcel(selected);
		if (excelBytes != null) {
			StreamResource resource = createStreamResource(selected, excelBytes);
			initiateDownload(resource, getItemId(selected));
		} else {
			Notification.show("Item not found", 3000, Notification.Position.MIDDLE);
		}
	}

	private StreamResource createStreamResource(T selected, byte[] excelBytes) {
		return new StreamResource("item_" + getItemId(selected) + ".xlsx", () -> new ByteArrayInputStream(excelBytes));
	}

	private void initiateDownload(StreamResource resource, String resourceId) {
		getUI().ifPresent(ui -> {
			registration = ui.getSession().getResourceRegistry().registerResource(resource);
			String resourceUrl = registration.getResourceUri().toString();
			ui.getPage().executeJs(
					"var link = document.createElement('a');" + "link.href = $0;"
							+ "link.download = 'item_' + $1 + '.xlsx';" + "document.body.appendChild(link);"
							+ "link.click();" + "document.body.removeChild(link);"
							+ "setTimeout(function() { $2.$server.unregisterResource(); }, 500);",
					resourceUrl, resourceId, getElement());
		});
	}

	/**
	 * Retrieves the unique identifier for the given item.
	 */
	protected abstract String getItemId(T item);
}
