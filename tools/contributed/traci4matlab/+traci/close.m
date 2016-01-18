function close()
%CLOSE Close the connection with the SUMO server.
%   CLOSE() Close the default connection with the SUMO server. If several
%   connections have to be closed, they must be enabled through the
%   traci.switch() command, and closed with CLOSE().

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: close.m 29 2015-10-13 13:21:27Z afacostag $

global connections message
import traci.constants

% Close and clear the tcp object
if ~isempty(connections)
    if isKey(connections,'') && ~isempty(connections(''))
        % Build the close command
        command = uint8(sscanf(constants.CMD_CLOSE,'%x'));
        message.queue = [message.queue command];
        message.string = [message.string uint8(1+1) command];
        
        % Send the close command
        traci.sendExact();
        
        activeConnection = connections('');
        activeConnection.socket.close();
        connections('') = [];
    end
end
