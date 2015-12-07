function IDCount = getIDCount()
%getIDCount Get the number of aeral detectors in the SUMO network.  

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
IDCount = traci.areal.getUniversal(constants.ID_COUNT, '');