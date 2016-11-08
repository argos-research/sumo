function completeRedYellowGreenDefinition = getCompleteRedYellowGreenDefinition(tlsID)
%getCompleteRedYellowGreenDefinition Get the complete traffic lights definition.
%   completeRedYellowGreenDefinition =
%   getCompleteRedYellowGreenDefinition(TLSID) Returns a 
%   traci.trafficlights.Logic object that describes the atributes of the 
%   traffic light definition including all the phase definitions.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getCompleteRedYellowGreenDefinition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
completeRedYellowGreenDefinition = traci.trafficlights.getUniversal(constants.TL_COMPLETE_DEFINITION_RYG, tlsID);