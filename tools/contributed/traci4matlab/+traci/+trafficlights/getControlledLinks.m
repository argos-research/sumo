function controlledLinks = getControlledLinks(tlsID)
%getControlledLinks Get the links controlled by the traffic light.
%   controlledLinks = getControlledLinks(tlsID) Returns a cell array 
%   containing the links controlled by the traffic light, sorted by the 
%   signal index and described by giving the incoming, outgoing, and via 
%   lane. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getControlledLinks.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
controlledLinks = traci.trafficlights.getUniversal(constants.TL_CONTROLLED_LINKS, tlsID);