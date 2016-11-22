function phase = getPhase(tlsID)
%getPhase Get the phase index.
%   phase = getPhase(TLSID) Returns the current phase index of
%   given trafficlights. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPhase.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
phase = traci.trafficlights.getUniversal(constants.TL_CURRENT_PHASE, tlsID);